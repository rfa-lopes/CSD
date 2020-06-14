[< Para trás](../README.md#Funcionalidades)

# Documentação da Autenticação

## Login
### Request

**URI**

```
POST/accounts/login
```

**Body**
```json
{
    "username":"rodrigo", 
    "password":"passwordsecreta"
}
```
```json
{
    "Token":"eyJ0eX...ovIxaTdiPGxEYQ"
}

### Response
- **200 OK** - Login efetuado com sucesso.
- **401 UNAUTHORIZED**  - Login fail.

---

[< Para trás](../README.md#Funcionalidades)
