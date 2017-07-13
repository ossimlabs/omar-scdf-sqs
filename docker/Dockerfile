FROM docker-registry-default.ossim.io/o2/omar-base:latest
MAINTAINER DigitalGlobe-RadiantBlue
LABEL com.digitalglobe.version="" 
RUN useradd -u 1001 -r -g 0 -d $HOME -s /sbin/nologin -c "Default Application User" omar
RUN mkdir /usr/share/omar
COPY run-script.sh /usr/share/omar
RUN bash -c chmod a+rx /usr/share/omar/run-script.sh
ENTRYPOINT ["/usr/share/omar/run-script.sh"]
RUN chown -R 1001:0 /usr/share/omar
RUN chown 1001:0 /usr/share/omar
RUN chmod -R g+rw /usr/share/omar
RUN find $HOME -type d -exec chmod g+x {} +
USER 1001
WORKDIR /usr/share/omar
VOLUME /tmp
EXPOSE 8080
RUN /usr/share/omar/run-script.sh omar-scdf-sqs-1.0.37-SNAPSHOT.jar
