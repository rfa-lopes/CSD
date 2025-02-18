[< Para trás](../../../README.md)

# WA3 - Auditoria ao sistema.
"This challenge is an auditing of the system about the security guarantees enabled/supported after the WA#2 Implementation"

---
## Configurações TLS entre Cliente e Servidor
Atualmente a conexão entre o Cliente da aplicação e o Servidor está suportada sob TLS na vertente Server-Side Authentication, com a autenticação do servidor a ser conseguida através de um certificado self-signed. A comunicação está apenas a aceitar TLSv1.2 e TLSv1.3. Estas configurações podem ser vistas [aqui](../../src/main/resources/application.properties).

---
## Configurações TLS do BFT-SMaRt
"Search/analyze that these channels are protected by confidentiality, integrity, non-replaying and authentication (Peer-Authentication versus Message Authentication)"

### "Is this communication secure (with the security guarantees provided and enabled by BFT SMaRt)?"
Analisando, o ficheiro [system.config](../../config/system.config) presente na diretoria config do repositório do BFT SMaRT, é possível concluir que por default o SSL/TLS se encontra ativo, com a versão do TLSv1.2 a ser utilizada, mas podendo ser modificada para versões alternativas. O mesmo acontece com os ciphersuits utilizados, que por defeito está definido com "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256", podendo ser utilizado numa variante sem AES ou com uso de algortimos RSA.
Atendendo às pre-sets utilizadas nas configurações referentes ao suporte de SSL/TLS, bem como às alternativas possíveis, classificamos esta vertente da comunicação como segura.

---
## Tempos de resposta do servidor ao cliente

![Grafico da média de tempos por operação](../Images/WA3_GraficoTempos.png)

🔶 Unordered operation

🔷 Ordered operation


**NOTA:** Ficheiro output dos testes automáticos [aqui](Test_4GOOD_Servers_NOFAILS.txt).

---
## Tests SSL - [Referência](https://testssl.sh/)

Após a auditoria (com o script testssl.sh) ao servidor foram encontradas algumas vulnerabilidades, como:

* Has server cipher order?  no (NOT ok)
* LOGJAM (CVE-2015-4000), experimental VULNERABLE (NOT ok): common prime: RFC2409/Oakley Group 2 (1024 bits),but no DH EXPORT ciphers

A primeira poderá ser mitigada se restringirmos as Cipher Suites aceites pelo servidor a apenas aquelas que nos dão um elevado nivel de segurança. Pois assim, mesmo que o cliente peça uma Cipher Suite fraca, o servidor ou não aceita, ou sendo esta uma das aceites pelo servidor, temos a garantia que é segura, e assim ter ou não server cipher order será irrelevante.

Para mitigar a vulnerabilidade LOGJAM poderiamos apenas utilizar Cipher Suites com curvas elipticas DH (ECDHE) de forma a dificultar a vida ao atacante.
Não aprofundámos mais este assunto, mas temos alguns apontadores futuros [aqui](https://weakdh.org/imperfect-forward-secrecy.pdf).

Outras possiveis vulnerabilidades foram encontradas por causa do tamanho das chaves DH que por terem apenas 1024 bits foram consideradas frageis pelo testssl. Mais uma vez, esta fraqueza poderia ser reforçada se houvesse uma maior restrição das Cipher Suites no lado do servidor. 

**NOTA:** Ficheiro output do testssl.sh [aqui](testsssl.txt).

---
[< Para trás](../../../README.md)
