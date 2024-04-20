FROM php:7.4-fpm

RUN apt update && apt upgrade -y 
RUN docker-php-ext-install mysqli && docker-php-ext-enable mysqli

COPY . /usr/src/myapp

WORKDIR /usr/src/myapp

CMD [ "php", "./fcm.php" ]