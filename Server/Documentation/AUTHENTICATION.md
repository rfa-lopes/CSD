[< Para trás](../README.md#Funcionalidades)

# Documentação da Autenticação ([JWT](https://jwt.io/))

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

### Response
- **200 OK** - Login efetuado com sucesso.

```json
{
    "Token":"eyJ0eX...ovIxaTdiPGxEYQ"
}
```

- **401 UNAUTHORIZED**  - Login fail.

---

[< Para trás](../README.md#Funcionalidades)
