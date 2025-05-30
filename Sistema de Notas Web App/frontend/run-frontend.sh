#!/bin/bash

echo "🌐 Abriendo la aplicación de notas en Google Chrome..."

HTML_FILE="$(pwd)/index.html"
HTML_WIN_PATH=$(cygpath -w "$HTML_FILE")

# Ruta por defecto de Google Chrome en Windows
CHROME_PATH="/c/Program Files/Google/Chrome/Application/chrome.exe"

# Si Chrome está instalado en otro lugar (ej. en Program Files (x86)), cambiá esta línea
if [[ ! -f "$CHROME_PATH" ]]; then
  CHROME_PATH="/c/Program Files (x86)/Google/Chrome/Application/chrome.exe"
fi

if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "win32" ]]; then
  if [[ -f "$CHROME_PATH" ]]; then
    "$CHROME_PATH" "$HTML_WIN_PATH"
  else
    echo "❌ No se encontró Google Chrome en la ruta esperada."
    echo "🔧 Por favor ajustá CHROME_PATH en el script."
  fi
else
  open -a "Google Chrome" "$HTML_FILE" 2>/dev/null || \
  google-chrome "$HTML_FILE" 2>/dev/null || \
  echo "❌ No se pudo abrir Google Chrome. Verificá si está instalado."
fi