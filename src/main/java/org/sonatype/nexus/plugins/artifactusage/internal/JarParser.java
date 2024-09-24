package org.sonatype.nexus.plugins.artifactusage.internal;

import org.sonatype.nexus.common.stateguard.StateGuardLifecycleSupport;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

@Singleton
@Named
public class JarParser extends StateGuardLifecycleSupport {

    private MavenReverseDependencyCalculator calculator;

    @Inject
    public JarParser(MavenReverseDependencyCalculator calculator) {
        this.calculator = calculator;
    }

    public void readMetaInfContent(InputStream inputStream) throws IOException {
        try (JarInputStream jarInputStream = new JarInputStream(inputStream)) {

            GAV dependentArtifact = null;
            ArrayList<GAV> mainArtifacts = new ArrayList<>();
            JarEntry entry;

            while ((entry = jarInputStream.getNextJarEntry()) != null) {
                if (entry.getName().startsWith("META-INF/") && !entry.isDirectory()) {
                    String entryName = entry.getName();
                    if (entryName.endsWith("/pom.xml")) {
                        String pomContent = readEntryContent(jarInputStream);
                        mainArtifacts.addAll(parsePomXml(pomContent));
                    } else if (entryName.endsWith("/pom.properties")) {
                        String propertiesContent = readEntryContent(jarInputStream);
                        dependentArtifact = parsePomProperties(propertiesContent);
                    }

                }
            }

            calculator.addReverseDependency(dependentArtifact, mainArtifacts);

        } catch (IOException e) {
            throw new IOException("Error reading .jar file: " + e.getMessage());
        }
    }

    ArrayList<GAV> parsePomXml(String xmlContent) {
        ArrayList<GAV> gavList = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xmlContent.getBytes(StandardCharsets.UTF_8)));

            NodeList dependencyNodes = doc.getElementsByTagName("dependency");
            for (int i = 0; i < dependencyNodes.getLength(); i++) {
                Element dependency = (Element) dependencyNodes.item(i);
                String groupId = dependency.getElementsByTagName("groupId").item(0).getTextContent();
                String artifactId = dependency.getElementsByTagName("artifactId").item(0).getTextContent();
                String version = dependency.getElementsByTagName("version").item(0).getTextContent();
                gavList.add(new GAV(groupId, artifactId, version));
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return gavList;
    }

    GAV parsePomProperties(String propertiesContent) {
        Properties properties = new Properties();
        try {
            properties.load(new ByteArrayInputStream(propertiesContent.getBytes(StandardCharsets.UTF_8)));
            String groupId = properties.getProperty("groupId");
            String artifactId = properties.getProperty("artifactId");
            String version = properties.getProperty("version");
            return new GAV(groupId, artifactId, version);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String readEntryContent(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
        return new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
    }
}
