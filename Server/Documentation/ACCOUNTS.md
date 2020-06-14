[< Para trás](../README.md#Funcionalidades)

# Documentação de Accounts

## Adicionar uma account
### Request
**URI**
```
POST/accounts/create
```
**Body**
```json
{
    "username":"rodrigo", 
    "password":"passwordsecreta"
}
```
### Response

```json
{
    "id":7
}
```

- **200 OK** - Account adicionada com sucesso
- **400 BAD REQUEST**  - Campos vazios
- **409 CONFLICT**  - Account username já existente

---


---

[< Para trás](../README.md#Funcionalidades)
