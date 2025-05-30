#!/bin/bash

echo "🚀 Iniciando aplicación completa (backend + frontend)..."

# Paso 1: Ejecutar backend (en segundo plano)
echo "🛠️ Ejecutando backend..."
( cd backend && ./run-backend.sh ) &

# Paso 2: Esperar unos segundos a que Spring Boot levante
echo "⏳ Esperando 10 segundos para que el backend esté disponible..."
sleep 10

# Paso 3: Abrir frontend en Google Chrome
echo "🌐 Abriendo frontend en Google Chrome..."
FRONTEND_HTML="$(pwd)/frontend/index.html"
HTML_WIN_PATH=$(cygpath -w "$FRONTEND_HTML")

CHROME_PATH="/c/Program Files/Google/Chrome/Application/chrome.exe"
[[ ! -f "$CHROME_PATH" ]] && CHROME_PATH="/c/Program Files (x86)/Google/Chrome/Application/chrome.exe"

if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "win32" ]]; then
  if [[ -f "$CHROME_PATH" ]]; then
    "$CHROME_PATH" "$HTML_WIN_PATH"
  else
    echo "⚠️ No se pudo abrir Chrome automáticamente."
  fi
else
  open -a "Google Chrome" "$FRONTEND_HTML" 2>/dev/null || google-chrome "$FRONTEND_HTML" 2>/dev/null
fi