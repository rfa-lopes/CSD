[< Para trás](../README.md#Funcionalidades)

# Documentação de Wallets

## Create Wallet
### Request
**URI**
```
POST /wallets/create
```
**Body**
```json
{
	"name":"Rodrigo Faria Lopes"
}
```
### Response
- **200 OK** - Wallet criada com sucesso.
- **400 BAD REQUEST**  - Nome para Wallet não aceite.

---

## Delete Wallet
### Request
**URI**
```
DELETE /wallets/delete/{id}
```
### Response
- **200 OK** - Wallet removida com sucesso.
- **404 NOT FOUNDT**  - ID da Wallet não existe.

---

## Obter quantia atual de uma Wallet
### Request
**URI**
```
GET /wallets/amout/{id}
```
### Response
- **200 OK**
```json
{
  "amount":9042
}
```
- **404 NOT FOUNDT**  - ID da Wallet não existe.

---

## Obter informação de uma Wallet
### Request
**URI**
```
GET /wallets/info/{id}
```
### Response
- **200 OK**
```json
{
  "id":1,
  "name":"Rodrigo Faria Lopes",
  "amount":9042
}
```
- **404 NOT FOUNDT**  - ID da Wallet não existe.

---

[< Para trás](../README.md#Funcionalidades)
