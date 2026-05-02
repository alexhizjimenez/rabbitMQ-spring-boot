package com.alexhiz.rabbitMQ.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String EXCHANGE_REPORTES = "reportes.exchange";
    public static final String QUEUE_REPORTES = "reportes.cola";
    public static final String ROUTING_KEY = "reportes.key";

   /**
     * Define la COLA (Queue) donde se almacenarán los mensajes físicamente.
     * Es el destino final donde los mensajes esperan a ser procesados.
     */
    @Bean
    public Queue colaReportes() {
        // QUEUE_REPORTES: El nombre único de la cola en el servidor.
        // true: Define que la cola es 'durable'. Si el servidor de RabbitMQ se reinicia, 
        // la cola y sus mensajes persistirán y no se perderán.
        return new Queue(QUEUE_REPORTES, true);
    }

    /**
     * Define el EXCHANGE (Intercambiador). 
     * Es el punto de entrada de los mensajes. El productor envía aquí, no a la cola.
     */
    @Bean
    public TopicExchange exchangeReportes() {
        // TopicExchange permite un enrutamiento flexible basado en patrones (wildcards).
        // Es el tipo de exchange más versátil para microservicios.
        return new TopicExchange(EXCHANGE_REPORTES);
    }

    /**
     * Define el BINDING (Vínculo).
     * Es la "regla de negocio" que conecta el Exchange con la Cola.
     */
    @Bean
    public Binding binding(Queue colaReportes, TopicExchange exchangeReportes) {
        // 1. bind(colaReportes): Selecciona la cola que acabamos de definir.
        // 2. to(exchangeReportes): La vincula al exchange creado arriba.
        // 3. with(ROUTING_KEY): Establece la "contraseña" o etiqueta. 
        // Solo los mensajes que lleguen al exchange con esta misma ROUTING_KEY 
        // serán redirigidos a esta cola específica.
        return BindingBuilder.bind(colaReportes)
                .to(exchangeReportes)
                .with(ROUTING_KEY);
    }
}
