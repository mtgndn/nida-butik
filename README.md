# Nida Butik Fashion Backend

Spring Boot ile geliştirilmiş moda satış uygulaması. Proje PostgreSQL üzerinde 3NF'e uygun müşteri, ürün, tedarikçi, sipariş, sipariş kalemi ve ödeme modelini kullanır. Nida Butik vitrini Zara benzeri minimal ve görsel odaklı bir koleksiyon sayfası olarak tasarlanmıştır.

## Teknolojiler

- Java 17
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- Spring Security
- Jakarta Validation
- PostgreSQL
- Docker Compose
- PostgreSQL JDBC 42.7.11

## Kurulum

PostgreSQL container'ını başlatın:

```powershell
docker compose up -d
```

Uygulamayı çalıştırın:

```powershell
.\mvnw.cmd spring-boot:run
```

Tarayıcı:

```text
http://localhost:8080
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

```json
{
  "customerId": 1,
  "items": [
    { "productId": 1, "quantity": 2 }
  ]
}
```

Ödeme alma:

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
- CSRF cookie tabanlı oturum kullanılmadığı için stateless Basic Auth API'de devre dışıdır; yazma işlemleri ADMIN rolüyle sınırlandırılmıştır.
- Frame içine gömülme engellenir ve hata cevapları hassas detay döndürmez.
- Spring Boot 4.0.6, CVE-2026-40976 için düzeltme içeren sürümdür.
- PostgreSQL JDBC 42.7.11, CVE-2026-42198 için düzeltme içeren sürümdür.

## Notlar

Docker Desktop çalışıyorken `docker compose up -d` komutu PostgreSQL'i `localhost:5432` üzerinde başlatır. İlk açılışta demo kategori, marka, tedarikçi, müşteri ve ürün verileri otomatik eklenir.
