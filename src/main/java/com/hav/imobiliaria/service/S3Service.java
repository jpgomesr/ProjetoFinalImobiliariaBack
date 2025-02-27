package com.hav.imobiliaria.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.bucket-name}")
    private String bucketName;

    public S3Service(@Value("${aws.access-key}") String accessKey,
                     @Value("${aws.secret-key}") String secretKey,
                     @Value("${aws.region}") String region) {


        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(
                AwsBasicCredentials.create(accessKey, secretKey)
        );


        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(credentialsProvider)
                .build();


    }

    public String uploadArquivo(MultipartFile file) throws IOException {
        // Gerar um nome único para evitar sobrescrever arquivos
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // Criar a requisição de upload
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(file.getContentType()) // Define o tipo do arquivo
                .build();

        // Enviar o arquivo para o S3
        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

        // Retornar a URL do arquivo salvo
        return urlDoArquivo(fileName);
    }


    public String urlDoArquivo(String fileName) {
        return s3Client.utilities().getUrl(GetUrlRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build()).toString();
    }

    public void excluirObjeto(String url){

        String key = url.replace("https://hav-bucket-c.s3.amazonaws.com/", "");

        this.s3Client.deleteObject(builder -> builder.bucket(this.bucketName).key(key));

    }
    public void excluirObjeto(List<String> urls){
        Stream<String> urlsFormatadas =
                urls.stream().map(url ->
                        url.replace("https://hav-bucket-c.s3.amazonaws.com/", ""));

        urlsFormatadas.forEach
                (url ->
                        this.s3Client.deleteObject(builder -> builder.bucket(this.bucketName).key(url)));
    }


}
