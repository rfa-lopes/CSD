[< Para trás](../README.md#Funcionalidades)

# Documentação de Transfers

---

## Adicionar dinheiro a uma Wallet
### Request
**URI**
```
POST/transfers/add
```
**Body**
```json
{
	"id":1,
	"amount":500
}
```
### Response
- **200 OK** - Quantia adicionada com sucesso.
- **404 NOT FOUND**  - Wallet ID não existe.
- **400 BAD REQUEST**  - Quantia demasiado elevada ou negativa.

---

## Remover dinheiro de uma Wallet
### Request
```
POST/transfers/remove
```
**Body**
```json
{
	"id":1,
	"amount":400
}
```

### Response
- **200 OK** - Quantia removida com sucesso.
- **404 NOT FOUND**  - Wallet ID não existe.
- **400 BAD REQUEST**  - Quantia demasiado elevada ou negativa.

---

## Tranferir dinheiro entre Wallets
### Request
```
POST/transfers/transfer
```
**Body**
```json
{
	"fromId":1,
	"toId":2,
	"amount":50
}
```

### Response
- **200 OK** - Quantia transferida com sucesso.
- **404 NOT FOUND**  - Wallet ID não existe.
- **400 BAD REQUEST**  - Quantia demasiado elevada ou negativa.
- **400 BAD REQUEST**  - Transferência para a mesma Wallet de origem.

---

## Obter todas as transferências
### Request
```
GET/transfers/globaltransfers
```

### Response
- **200 OK**
```json
[
	{
		"id": 1,
		"fromId": 1,
		"toId": 2,
		"amount": 50,
		"date": "2020/03/20 22:45:57"
	},
	{
		"id": 2,
		"fromId": 2,
		"toId": 1,
		"amount": 50,
		"date": "2020/03/20 22:46:25"
	}
]
```

---

## Obter todas as transferências enviadas e recebidas de uma Wallet
### Request
```
GET/wallettransfers/{id}
```

### Response
- **200 OK**
```json
[
	{
		"id": 1,
		"fromId": 1,
		"toId": 2,
		"amount": 50,
		"date": "2020/03/20 22:45:57"
	},
	{
		"id": 2,
		"fromId": 2,
		"toId": 1,
		"amount": 50,
		"date": "2020/03/20 22:46:25"
	}
]
```
- **404 NOT FOUND**  - Wallet ID não existe.

---

[< Para trás](../README.md#Funcionalidades)
