module sabo.yahvya.githubdirectoryextractor {
    requires javafx.controls;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires java.logging;

    exports sabo.yahvya.githubdirectoryextractor;
    exports sabo.yahvya.githubdirectoryextractor.views.utils;
    exports sabo.yahvya.githubdirectoryextractor.views.views;
    exports sabo.yahvya.githubdirectoryextractor.resources.utils;
    exports sabo.yahvya.githubdirectoryextractor.views.components;
    exports sabo.yahvya.githubdirectoryextractor.utils;
    exports sabo.yahvya.githubdirectoryextractor.githubextractor.extractor;
    exports sabo.yahvya.githubdirectoryextractor.githubextractor.api;
}
