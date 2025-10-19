#!/usr/bin/env pwsh
# Script para construir frontend y backend

Write-Host "🏗️ Construyendo proyecto completo..." -ForegroundColor Green

# Build Frontend
Write-Host "`n📱 Construyendo Frontend..." -ForegroundColor Cyan
Set-Location frontend
npm run build
if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Error en build de frontend" -ForegroundColor Red
    exit 1
}

# Build Backend
Write-Host "`n🔧 Construyendo Backend..." -ForegroundColor Cyan
Set-Location ../backend
./mvnw clean package -DskipTests
if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Error en build de backend" -ForegroundColor Red
    exit 1
}

Set-Location ..
Write-Host "`n✅ Build completado exitosamente" -ForegroundColor Green
Write-Host "📦 Frontend: frontend/dist/" -ForegroundColor Yellow
Write-Host "📦 Backend: backend/target/*.jar" -ForegroundColor Yellow