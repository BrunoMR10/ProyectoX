package com.bmr.xproyect.ServiceHelper;

import android.os.Environment;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import java.io.DataOutput;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DriveServiceHelper {
    private final Executor mExecutor = Executors.newSingleThreadExecutor();
    private final Drive mDriveService;
    public DriveServiceHelper(Drive driveService) {
        mDriveService = driveService;
    }
    public Task<String> createFile(String FolderID,String Name, String Nomenclatura) {
        return Tasks.call(mExecutor, () -> {
            File metadata = new File()
                    .setParents(Collections.singletonList(FolderID))
                    .setMimeType("application/pdf")
                    .setName(Name);

            String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + java.io.File.separator;
            String rutacarpeta = Nomenclatura;



            java.io.File filePath = new java.io.File(ExternalStorageDirectory  + rutacarpeta + Name);
            // Specify media type and file-path for file.
            FileContent mediaContent = new FileContent("application/pdf", filePath);

            File googleFile = mDriveService.files().create(metadata,mediaContent)
                    .setSupportsTeamDrives(true)
                    .execute();

            if (googleFile == null) {
                throw new IOException("Null result when requesting file creation.");
            }

            return googleFile.getId();
        });
    }

    public Task<String> SubirFoto(String FolderID,String Name,String Name2, String[]Datos) {
        return Tasks.call(mExecutor, () -> {
            File metadata = null;
            metadata = new File()
                        .setParents(Collections.singletonList(FolderID))
                        .setMimeType("image/png")
                        .setName(Name2);

            String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + java.io.File.separator;
            String rutacarpeta = "ProyectoX/"+Datos[1]+"/" + Datos[0] + "/" + Datos[0] + "(CF)/";
            java.io.File filePath = new java.io.File(ExternalStorageDirectory  + rutacarpeta + Name);
            // Specify media type and file-path for file.
            FileContent mediaContent = new FileContent("image/png", filePath);

            File googleFile = mDriveService.files().create(metadata,mediaContent)
                    .setSupportsTeamDrives(true)
                    .execute();

            if (googleFile == null) {
                throw new IOException("Null result when requesting file creation.");
            }

            return googleFile.getId();
        });
    }
    public Task<String[]> createFolder(String Ticket,String Where,String Fecha) {
        return (Task<String[]>) Tasks.call(mExecutor, () -> {
            String Nomenclatura;
            int PosIn,PosFin;
            String[] Datos;
            String id = null;
            SimpleDateFormat parseador = new SimpleDateFormat("dd-MM-yy");
            SimpleDateFormat formateador = new SimpleDateFormat("MM");
            SimpleDateFormat formateador2 = new SimpleDateFormat("yy");
            SimpleDateFormat formateador3 = new SimpleDateFormat("MMMyy");
            Date date = null;
            try {
                date = parseador.parse(Fecha);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int fecha = Integer.parseInt(formateador.format(date));
            int año = Integer.parseInt(formateador2.format(date));
            String Clave = String.valueOf(fecha)+String.valueOf(año);
            System.out.println(año);
            System.out.println(fecha);
            System.out.println(Clave);
            String Trimestre = "";
            if (Clave.equals("622") || Clave.equals("722")||Clave.equals("822")){
                if (Where.equals("Preventivo")){
                    id= "1zLVdExoF7KXV2qd7O5H4svcmvvbocQaD";
                }else if (Where.equals("Correctivo")){
                    id = "1ZmHQtePg0LGd2IQYwdXedheDVoax1h4L";
                }else{
                    id= "1h_8OKoeofR25SRfSggXOYBgeLL20Ks5E";
                }
            }
            else if (Clave.equals("922") || Clave.equals("1022")||Clave.equals("1122")){
                if (Where.equals("Preventivo")){
                     id= "1zLVdExoF7KXV2qd7O5H4svcmvvbocQaD";
                }else if (Where.equals("Correctivo")){
                    id = "1ZmHQtePg0LGd2IQYwdXedheDVoax1h4L";
                }else{
                    id= "1h_8OKoeofR25SRfSggXOYBgeLL20Ks5E";
                }
            }
            else if (Clave.equals("1222") || Clave.equals("123")||Clave.equals("223")){
                if (Where.equals("Preventivo")){
                    id= "1zLVdExoF7KXV2qd7O5H4svcmvvbocQaD";
                }else if (Where.equals("Correctivo")){
                    id = "1ZmHQtePg0LGd2IQYwdXedheDVoax1h4L";
                }else{
                    id= "1h_8OKoeofR25SRfSggXOYBgeLL20Ks5E";
                }
            }
            else if (Clave.equals("323") || Clave.equals("423")||Clave.equals("523")){
                if (Where.equals("Preventivo")){
                    if (Clave.equals("323")) id= "1jhve2ImosqItc4LPb0O5xEc3iSHqYmGc";
                    if (Clave.equals("423")) id= "1jk5rBMLU2JBAbs_iD9S-iwRjkKo4YTPO";
                    if (Clave.equals("523")) id= "1jrWiAhPymUUmDTPDj6kQugHm9WgNErDZ";
                }else if (Where.equals("Correctivo")){
                    id = "1TKmjXqCkag3TqjuTi1IkqJI3IK5CMACw";
                }else{
                    id= "1dAhPJrzyLQfysvPp5iMK0pgsz-VKM9h_";
                }
            }
            else if (Clave.equals("623") || Clave.equals("723")||Clave.equals("823")){
                if (Where.equals("Preventivo")){
                    if (Clave.equals("623")) id = "1izWuqc4CSx5gBKI-RzdJiAnIHoGWjgVQ";
                    if (Clave.equals("723")) id = "15EHrHngrG28aNKJ1JjRvB3M_PkxbOIsc";
                    if (Clave.equals("823")) id = "1O9HcwLI7RuQHUiRBe8EBlNMcmjVZ9WqB";
                }else if (Where.equals("Correctivo")){
                    id = "1vMtc81dQwiZ2rIaiWKcfxPIqiDMZe1x4";
                }else{
                    id= "16ohQYPNncTtpLFj8ysY_IE95GQExpPrM";
                }

            }
            else if (Clave.equals("923") || Clave.equals("1023")||Clave.equals("1123")){
                if (Where.equals("Preventivo")){
                    if (Clave.equals("923")) id = "1Q_48C76VSNy44aWEGIJypYKpeQvlh6su";
                    if (Clave.equals("1023")) id = "1dqvlYHkomcR4eXoYcK-hCs87BlcETDcf";
                    if (Clave.equals("1123")) id = "1AyThOzCPIgT7jxegotsH1vFYK7_t9uTW";
                }else if (Where.equals("Correctivo")){
                    id = "1cv0O-3HfoGckxH8rdMi_5EJ-IKTzv4k_";
                }else{
                    id= "1biXF3ASjulr-HzNhV9VCk1-vrLZKVdWf";
                }
            }
            else if (Clave.equals("1223") || Clave.equals("124")||Clave.equals("224")){
                if (Where.equals("Preventivo")){
                    if (Clave.equals("1223")) id = "1I5HaHJ6SiLDhg9YNJWwaBYfTSMP05Zjg";
                    if (Clave.equals("124")) id = "1kQtqoFREkwYfw5zoTQcF8YbuWGmszVIt";
                    if (Clave.equals("224")) id = "1_X1RJY1o4tGWDtQk7DdCsTB_td_aOwr_";
                }else if (Where.equals("Correctivo")){
                    id = "1QyzyevDfnRxGC9jUtrMhfBsSYdxaBaG9";
                }else{
                    id= "1dw1yyD4zL_FjI9ojXXzzRU2BePs8GXLW";
                }
            }
            else if (Clave.equals("324") || Clave.equals("424")||Clave.equals("524")){
                if (Where.equals("Preventivo")){
                    if (Clave.equals("324")) id = "1UkdzkIXq7MI-3vhu9U-Z31JX00LaIWAr";
                    if (Clave.equals("424")) id = "1jaDcJy7EKaMzu7-M8T7Thz3Df5AgN_VM";
                    if (Clave.equals("524")) id = "1PLlyjnde44UNQAJ_yWU0OleCXI3IfI0G";
                }else if (Where.equals("Correctivo")){
                    id = "1mbJ2dPZdqz4fCZ19if3sNi6dX5uDnCNQ";
                }else{
                    id= "1QUNmasbx-kRNTlwJ2hyMKt8v_kLGHTwv";
                }
            }   








            // File's metadata.
            File fileMetadata = new File();
            //fileMetadata.setName("Test");
            fileMetadata.setParents(Collections.singletonList(id));
            fileMetadata.setName(Ticket);
            fileMetadata.setMimeType("application/vnd.google-apps.folder");



            try {
                File googleFile = mDriveService.files().create(fileMetadata)
                        .setSupportsTeamDrives(true)
                        .setFields("id")
                        .execute();
                System.out.println("Folder ID: " + googleFile.getId());
                System.out.println("Folder LINK: " + googleFile.getWebViewLink());

                Datos= new String[]{
                        googleFile.getId(),
                        googleFile.getWebContentLink()
                };
                return Datos;
            } catch (GoogleJsonResponseException e) {
                // TODO(developer) - handle error appropriately
                System.err.println("Unable to create folder: " + e.getDetails());
                throw e;
            }

        });
    }

    public Task<String> createFolderFotos(String FolderID,String Ticket) {
        return Tasks.call(mExecutor, () -> {
            // File's metadata.
            File fileMetadata = new File();
            //fileMetadata.setName("Test");
            fileMetadata.setParents(Collections.singletonList(FolderID));
            fileMetadata.setName(Ticket+"(CF)");
            fileMetadata.setMimeType("application/vnd.google-apps.folder");



            try {
                File googleFile = mDriveService.files().create(fileMetadata)
                        .setSupportsTeamDrives(true)
                        .setFields("id")
                        .execute();
                System.out.println("Folder ID: " + googleFile.getId());
                return googleFile.getId();
            } catch (GoogleJsonResponseException e) {
                // TODO(developer) - handle error appropriately
                System.err.println("Unable to create folder: " + e.getDetails());
                throw e;
            }

        });
    }




}
