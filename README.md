# Nida Butik Fashion Backend

Spring Boot ile geliştirilmiş moda satış uygulaması. Proje PostgreSQL üzerinde 3NF'e uygun müşteri, ürün, tedarikçi, sipariş, sipariş kalemi ve ödeme modelini kullanır. Nida Butik vitrini minimal ve görsel odaklı bir koleksiyon sayfası olarak tasarlanmıştır.

## Teknolojiler

- Java 17 veya üzeri
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- Spring Security
- Jakarta Validation
- PostgreSQL
- Docker Compose
- PostgreSQL JDBC 42.7.11

## Kurulum ve Çalıştırma

PostgreSQL container'ını başlatın:

```powershell
docker compose up -d
```

Uygulamayı çalıştırın:

```powershell
.\mvnw.cmd spring-boot:run
```

Tarayıcıdan açın:

```text
http://localhost:8080
```

8080 portu doluysa eski çalışan uygulamayı kapatın veya farklı port kullanın:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.arguments=--server.port=8081"
```

Bu durumda adres:

```text
http://localhost:8081
```

## Varsayılan Kullanıcılar

- USER: `user` / `1234`
- ADMIN: `admin` / `admin123`

RBAC kuralı:

- `GET /api/**`: USER veya ADMIN
- `POST /api/**`, `PUT /api/**`, `DELETE /api/**`: ADMIN

## Temel API Örnekleri

Ürünleri filtreleme:

```text
GET /api/products/filter?minPrice=1000&maxPrice=3000&model=Elbise&brand=Zara%20Studio
```

En çok ürün satın alan ilk 8 kadın müşteri:

```text
GET /api/customers/top-buyers?gender=FEMALE
```

Sipariş oluşturma:

```http
POST /api/orders
Authorization: Basic admin:admin123
Content-Type: application/json
```

```json
{
  "customerId": 1,
  "items": [
    { "productId": 1, "quantity": 2 }
  ]
}
```

Ödeme alma:

```http
POST /api/payments
Authorization: Basic admin:admin123
Content-Type: application/json
```

```json
{
  "orderId": 1,
  "method": "CREDIT_CARD",
  "transactionCode": "TXN-2026-0001"
}
```

Rollback demosu:

```text
POST /api/payments/rollback-demo
```

## Güvenlik

- SQL injection riskine karşı Spring Data JPA repository ve parametreli sorgu altyapısı kullanılır.
- DTO ve `jakarta.validation` ile dış girdiler sınırlandırılır.
- XSS riskini azaltmak için frontend tarafında `textContent` kullanılır ve CSP header'ı tanımlıdır.
- CSRF, stateless Basic Auth API kullanıldığı için devre dışıdır; yazma işlemleri ADMIN rolüyle sınırlandırılmıştır.
- Frame içine gömülme engellenir ve hata cevapları hassas detay döndürmez.
- Spring Boot 4.0.6 ve PostgreSQL JDBC 42.7.11 sürümleri kullanılır.

## Sunum Kontrol Listesi

1. Docker Desktop açık olsun.
2. `docker compose up -d` komutunu çalıştırın.
3. `.\mvnw.cmd spring-boot:run` komutunu çalıştırın.
4. Terminalde `Started NidaButikApplication` satırını görün.
5. Tarayıcıda `http://localhost:8080` adresini açın.

## Notlar

Docker Desktop çalışıyorken `docker compose up -d` komutu PostgreSQL'i `localhost:5432` üzerinde başlatır. İlk açılışta demo kategori, marka, tedarikçi, müşteri ve ürün verileri otomatik eklenir.
