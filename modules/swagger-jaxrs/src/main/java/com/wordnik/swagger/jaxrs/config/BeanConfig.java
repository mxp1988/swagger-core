package com.wordnik.swagger.jaxrs.config;

import com.wordnik.swagger.models.*;
import com.wordnik.swagger.jaxrs.Reader;

import com.wordnik.swagger.annotations.Api;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;

public class BeanConfig {
  Reader reader = new Reader();

  String resourcePackage;
  String title;
  String description;
  String termsOfServiceUrl;
  String contact;
  String license;
  String licenseUrl;
  // String filterClass;

  Info info;
  String host;
  String basePath;

  public String getResourcePackage() {
    return this.resourcePackage;
  }
  public void setResourcePackage(String resourcePackage) {
    this.resourcePackage = resourcePackage;
  }

  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  public String getTermsOfServiceUrl() {
    return termsOfServiceUrl;
  }
  public void setTermsOfServiceUrl(String termsOfServiceUrl) {
    this.termsOfServiceUrl = termsOfServiceUrl;
  }

  public String getContact() {
    return contact;
  }
  public void setContact(String contact) {
    this.contact = contact;
  }

  public String getLicense() {
    return license;
  }
  public void setLicense(String license) {
    this.license = license;
  }

  public String getLicenseUrl() {
    return licenseUrl;
  }
  public void setLicenseUrl(String licenseUrl) {
    this.licenseUrl = licenseUrl;
  }

  public Info getInfo() {
    return info;
  }
  public void setInfo(Info info) {
    this.info = info;
  } 

  public String getHost() {
    return host;
  }
  public void setHost(String host) {
    this.host = host;
  }

  public String getBasePath() {
    return basePath;
  }
  public void setBasePath(String basePath) {
    this.basePath = basePath;
  }

  public void setScan(boolean shouldScan) {
    ConfigurationBuilder config = new ConfigurationBuilder()
      .setUrls(ClasspathHelper.forPackage(resourcePackage))
      .setScanners(new TypeAnnotationsScanner(), new SubTypesScanner());

    this.info = new Info()
      .description(description)
      .title(title);

    if(contact != null)
      this.info.contact(new Contact()
        .name(contact));
    if(license != null && licenseUrl != null)
      this.info.license(new License()
        .name(license)
        .url(licenseUrl));

    Set<Class<?>> classes = new Reflections(config).getTypesAnnotatedWith(Api.class);
    if(classes != null)
      reader.read(classes)
        .title(title)
        .host(host)
        .basePath(basePath)
        .info(info);
  }

  public Swagger getSwagger() {
    return reader.getSwagger();
  }
}