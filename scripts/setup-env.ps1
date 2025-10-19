#!/usr/bin/env pwsh
# Script para configurar variables de entorno

Write-Host "⚙️ Configurando variables de entorno..." -ForegroundColor Green

$envExample = "backend/.env.example"
$envFile = "backend/.env"

if (Test-Path $envFile) {
    Write-Host "⚠️ El archivo .env ya existe" -ForegroundColor Yellow
    $overwrite = Read-Host "¿Deseas sobrescribirlo? (s/n)"
    if ($overwrite -ne "s") {
        Write-Host "❌ Operación cancelada" -ForegroundColor Red
        exit 0
    }
}

Copy-Item -Path $envExample -Destination $envFile
Write-Host "✅ Archivo .env creado desde .env.example" -ForegroundColor Green
Write-Host "⚠️ IMPORTANTE: Edita backend/.env con tus credenciales reales" -ForegroundColor Yellow