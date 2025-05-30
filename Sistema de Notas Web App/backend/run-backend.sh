#!/bin/bash

DB_NAME="ensolvers_notes"
MYSQL_USER="root"
MYSQL_PASSWORD="admin"

echo "🛠️  Inicializando base de datos y backend..."

# Detectar sistema operativo
if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "win32" ]]; then
  echo "🪟 Sistema: Windows (Git Bash)"

  # Intentar detectar ruta MySQL si no fue editada
  MYSQL_PATH="/c/Program Files/MySQL/MySQL Server 8.0/bin/mysql"

  if [[ ! -f "$MYSQL_PATH" ]]; then
    echo "❌ No se encontró MySQL en: $MYSQL_PATH"
    echo "🔧 Editá run.sh y ajustá la variable MYSQL_PATH"
    exit 1
  fi

  "$MYSQL_PATH" -u"$MYSQL_USER" -p"$MYSQL_PASSWORD" -e "CREATE DATABASE IF NOT EXISTS $DB_NAME"

  echo "🚀 Iniciando Spring Boot..."
  cmd.exe /C "mvnw.cmd spring-boot:run"

else
  echo "🐧 Sistema: Linux/macOS"

  mysql -u"$MYSQL_USER" -p"$MYSQL_PASSWORD" -e "CREATE DATABASE IF NOT EXISTS $DB_NAME"

  echo "🚀 Iniciando Spring Boot..."
  ./mvnw spring-boot:run
fi