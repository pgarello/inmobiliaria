import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.client.http.FileContent;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

public class GoogleDriveUploader {

    private static final String APPLICATION_NAME = "Google Drive Upload";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    //private static final java.io.File TOKENS_DIRECTORY = new java.io.File("tokens");
    private static final java.io.File TOKENS_DIRECTORY = new java.io.File("src/main/resources/tokens");

    // Ajusta los alcances según lo que necesites. DRIVE_FILE permite subir archivos creados por la app.
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_FILE);

    
    public static void main(String[] args) throws Exception {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = authorize(HTTP_TRANSPORT);

        Drive driveService = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();

        // --- NUEVA LÓGICA ---
        // Definimos el nombre de la carpeta donde queremos guardar los backups
        String folderName = "Mis_Backups_SQL"; 
        String folderId = getOrCreateFolder(driveService, folderName);

        java.io.File localFile = new java.io.File("/tmp/inmobiliaria12012026.sql");

        if (localFile.exists()) {
            // Pasamos el folderId obtenido (sea creado o encontrado)
            uploadFileToGoogleDrive(driveService, localFile, folderId);
        } else {
            System.err.println("El archivo local no existe.");
        }
    }
    
    
    
    public static String getOrCreateFolder(Drive service, String folderName) throws IOException {
        String folderId = null;

        // 1. Intentar buscar si la carpeta ya existe
        // q: name = 'Nombre' and mimeType = 'application/vnd.google-apps.folder' and trashed = false
        Drive.Files.List request = service.files().list()
                .setQ("name = '" + folderName + "' and mimeType = 'application/vnd.google-apps.folder' and trashed = false")
                .setSpaces("drive")
                .setFields("files(id, name)");

        com.google.api.services.drive.model.FileList result = request.execute();
        List<com.google.api.services.drive.model.File> files = result.getFiles();

        if (files != null && !files.isEmpty()) {
            // Si existe, tomamos el ID
            folderId = files.get(0).getId();
            System.out.println("Carpeta encontrada con ID: " + folderId);
        } else {
            // 2. Si no existe, la creamos
            com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
            fileMetadata.setName(folderName);
            fileMetadata.setMimeType("application/vnd.google-apps.folder");

            com.google.api.services.drive.model.File folder = service.files().create(fileMetadata)
                    .setFields("id")
                    .execute();
            folderId = folder.getId();
            System.out.println("Carpeta creada nueva con ID: " + folderId);
        }

        return folderId;
    }
    
    
    
    // Método para autenticar y obtener las credenciales de Google (carga desde src/main/resources o classpath)
    public static Credential authorize(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        java.lang.ClassLoader cl = Thread.currentThread().getContextClassLoader();

        System.out.println("[DEBUG] user.dir=" + System.getProperty("user.dir"));
        System.out.println("[DEBUG] java.class.path=" + System.getProperty("java.class.path"));

        String[] candidateResources = new String[]{
                "credentials.json",
                "resources/credentials.json",
                "src/resources/credentials.json",
                "src/main/resources/credentials.json"
        };

        InputStream in = null;
        for (String candidate : candidateResources) {
            if (cl != null) {
                in = cl.getResourceAsStream(candidate);
            } else {
                in = GoogleDriveUploader.class.getResourceAsStream("/" + candidate);
            }
            System.out.println("Trying classpath resource: '" + candidate + "' -> " + (in != null));
            if (in != null) break;
        }

        // También probar getResourceAsStream con leading slash
        if (in == null) {
            String[] leading = new String[]{"/credentials.json", "/resources/credentials.json"};
            for (String cand : leading) {
                in = GoogleDriveUploader.class.getResourceAsStream(cand);
                System.out.println("Trying class resource: '" + cand + "' -> " + (in != null));
                if (in != null) break;
            }
        }

        // Fallback a archivos en el árbol del proyecto (archivo en disco)
        if (in == null) {
            String userDir = System.getProperty("user.dir");
            String[] diskPaths = new String[]{
                    "src/resources/credentials.json",
                    "src/main/resources/credentials.json",
                    "src/credentials.json",
                    "credentials.json"
            };
            for (String p : diskPaths) {
                java.io.File alt = new java.io.File(userDir, p);
                System.out.println("Checking file: " + alt.getAbsolutePath() + " -> exists=" + alt.exists());
                if (alt.exists()) {
                    in = new FileInputStream(alt);
                    break;
                }
            }
        }

        if (in == null) {
            throw new FileNotFoundException("No se encontró 'credentials.json' en classpath ni en src/resources ni en src/main/resources ni en el directorio actual. Revisa que el archivo exista y que esté en src/main/resources o en src/resources.");
        }

        // Usar try-with-resources y UTF-8 para leer el JSON de credenciales
        GoogleClientSecrets clientSecrets;
        try (InputStream is = in;
             InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, reader);
        }

        // Construir el flujo de autorización de Google OAuth
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT,
                JSON_FACTORY,
                clientSecrets,
                SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(TOKENS_DIRECTORY)) // Para guardar tokens de acceso
                .setAccessType("offline")
                .build();

        // Ejecutar la autorización OAuth2.0 (abrirá un puerto local y puede abrir el navegador)
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        return credential;
    }

    // Método para subir un archivo a Google Drive        
    // uploadFileToGoogleDrive(driveService, localFile, "TU_ID_DE_CARPETA_AQUI");
    public static void uploadFileToGoogleDrive(Drive service, java.io.File localFile, String folderId) throws IOException {
    	// 1. Definir el tipo de contenido (MIME type)
        String mimeType = "application/octet-stream";
        FileContent mediaContent = new FileContent(mimeType, localFile);

        // 2. Crear el metadata del archivo
        File fileMetadata = new File();
        fileMetadata.setName(localFile.getName());
        
        // --- AGREGAR DESCRIPCIÓN ---
        fileMetadata.setDescription("Backup automático de base de datos generado el: " + new java.util.Date());

        // --- ASIGNAR CARPETA ---
        if (folderId != null && !folderId.isEmpty()) {
            fileMetadata.setParents(Collections.singletonList(folderId));
        }

        // 3. Ejecutar la subida indicando que queremos recibir el ID y la descripción en la respuesta
        File uploadedFile = service.files().create(fileMetadata, mediaContent)
                .setFields("id, description, parents")
                .execute();

        System.out.println("----------------------------------------------");
        System.out.println("Archivo subido exitosamente.");
        System.out.println("ID: " + uploadedFile.getId());
        System.out.println("Descripción: " + uploadedFile.getDescription());
        System.out.println("Carpeta ID: " + uploadedFile.getParents());
        System.out.println("----------------------------------------------");
    }
    
    
    
}