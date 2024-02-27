package org.jjv.service;

import com.adobe.pdfservices.operation.PDFServices;
import com.adobe.pdfservices.operation.PDFServicesMediaType;
import com.adobe.pdfservices.operation.PDFServicesResponse;
import com.adobe.pdfservices.operation.auth.Credentials;
import com.adobe.pdfservices.operation.auth.ServicePrincipalCredentials;
import com.adobe.pdfservices.operation.exception.ServiceApiException;
import com.adobe.pdfservices.operation.io.Asset;
import com.adobe.pdfservices.operation.io.StreamAsset;
import com.adobe.pdfservices.operation.pdfjobs.jobs.ExtractPDFJob;
import com.adobe.pdfservices.operation.pdfjobs.params.extractpdf.ExtractElementType;
import com.adobe.pdfservices.operation.pdfjobs.params.extractpdf.ExtractPDFParams;
import com.adobe.pdfservices.operation.pdfjobs.result.ExtractPDFResult;
import org.apache.commons.io.IOUtils;
import org.jjv.Globals;
import org.jjv.instance.LogCollectedInstance;
import org.jjv.instance.PathInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.jjv.Globals.*;
import static org.jjv.Globals.SECRET_ENV;

public class ExtractData {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExtractData.class);
    public static void ExtractTextTable() throws IOException, ServiceApiException {
        InputStream inputStream = Files.newInputStream(new File(PathInstance.getPath()).toPath());

        LogCollectedInstance.addLog("Setting credentials...");
        Credentials credentials = new ServicePrincipalCredentials(System.getenv(CLIENT_ENV), System.getenv(SECRET_ENV));
        PDFServices pdfServices = new PDFServices(credentials);

        LogCollectedInstance.addLog("Uploading to PDF Services...");
        Asset asset = pdfServices.upload(inputStream, PDFServicesMediaType.PDF.getMediaType());
        ExtractPDFParams extractPDFParams = ExtractPDFParams.extractPDFParamsBuilder()
                .addElementsToExtract(Arrays.asList(ExtractElementType.TEXT, ExtractElementType.TABLES))
                .build();

        LogCollectedInstance.addLog("Initializing extracting job...");
        ExtractPDFJob extractPDFJob = new ExtractPDFJob(asset).setParams(extractPDFParams);

        LogCollectedInstance.addLog("Waiting for service response...");
        String location = pdfServices.submit(extractPDFJob);
        PDFServicesResponse<ExtractPDFResult> pdfServicesResponse = pdfServices.getJobResult(location, ExtractPDFResult.class);

        LogCollectedInstance.addLog("Getting content...");
        Asset resultAsset = pdfServicesResponse.getResult().getResource();
        StreamAsset streamAsset = pdfServices.getContent(resultAsset);

        String outputFilePath = createOutputFilePath();
        LOGGER.info(String.format("Saving asset at %s", outputFilePath));

        LogCollectedInstance.addLog(String.format("Saving asset at %s", outputFilePath));

        OutputStream outputStream = Files.newOutputStream(new File(outputFilePath).toPath());
        IOUtils.copy(streamAsset.getInputStream(), outputStream);
        outputStream.close();

        LogCollectedInstance.addLog("Operation finalized...");
    }

    private static String createOutputFilePath() throws IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        String timeStamp = dateTimeFormatter.format(now);
        Files.createDirectories(Paths.get(RESOURCES_PATH));
        System.out.println(RESOURCES_PATH + timeStamp + ".zip");
        return (RESOURCES_PATH + timeStamp + ".zip");
    }
}
