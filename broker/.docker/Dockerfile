FROM rabbitmq:3.13.3-management-alpine

# Habilitamos los plugins MQTT y Web-MQTT en tiempo de build
RUN rabbitmq-plugins enable --offline rabbitmq_mqtt \
    && rabbitmq-plugins enable --offline rabbitmq_web_mqtt
    && rabbitmqctl enable_feature_flag all --offline

# Puerto MQTT: 1883
# Puerto AMQP: 5672
# Puerto HTTP Admin: 15672
EXPOSE 5672 15672 1883

# Comando de arranque normal
CMD ["rabbitmq-server"]
