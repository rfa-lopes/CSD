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
	"name":"Rodrigo"
}
```
### Response
- **200 OK** - Wallet criada com sucesso.
- **400 BAD REQUEST**  - Nome para Wallet não aceite.

---

## Create Wallet
### Request
**URI**
```
DELETE /wallets/delete/{id}
```
### Response
- **200 OK** - Wallet removida com sucesso.
- **404 NOT FOUNDT**  - ID da Wallet não existe.

---


---



---

[< Para trás](../README.md#Funcionalidades)
