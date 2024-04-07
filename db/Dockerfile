FROM php:7.4-fpm

RUN apt update && apt upgrade -y 
RUN docker-php-ext-install mysqli && docker-php-ext-enable mysqli

COPY ./php /usr/src/myapp

WORKDIR /usr/src/myapp

CMD [ "php", "-S", "0.0.0.0:80", "./gestordb.php" ]