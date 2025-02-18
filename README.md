# Utilização de Prefixos no GitHub

Este repositório utiliza uma convenção de prefixos para organizar e categorizar issues, pull requests e commits. A adoção desses prefixos ajuda a manter o projeto organizado, facilita a identificação do tipo de tarefa e melhora a clareza durante o desenvolvimento.

## Prefixos Comuns

Aqui estão os prefixos mais utilizados e seus significados:

- **feat**: Indica a adição de uma nova funcionalidade ao projeto.
  - Exemplo: `feat: adiciona suporte a autenticação via OAuth`

- **fix**: Corrige um bug ou problema no código.
  - Exemplo: `fix: resolve erro de validação no formulário`

- **docs**: Atualizações ou adições na documentação.
  - Exemplo: `docs: atualiza guia de instalação`

- **style**: Alterações relacionadas à formatação do código (espaçamento, indentação, etc.) que não afetam a funcionalidade.
  - Exemplo: `style: ajusta indentação no arquivo principal`

- **refactor**: Refatoração de código que não adiciona novas funcionalidades nem corrige bugs.
  - Exemplo: `refactor: melhora estrutura do módulo de autenticação`

- **test**: Adição ou modificação de testes.
  - Exemplo: `test: adiciona testes para o módulo de usuários`

- **chore**: Tarefas de manutenção, como atualizações de dependências ou configurações.
  - Exemplo: `chore: atualiza dependências do projeto`

- **ci**: Alterações relacionadas à integração contínua (CI).
  - Exemplo: `ci: configura GitHub Actions para testes automatizados`

- **perf**: Melhorias de desempenho.
  - Exemplo: `perf: otimiza consultas ao banco de dados`

- **build**: Alterações que afetam o sistema de build ou dependências externas.
  - Exemplo: `build: atualiza configuração do Webpack`

## Como Usar

1. Ao criar uma **issue**, utilize o prefixo correspondente ao tipo de tarefa.
   - Exemplo: `[feat] Adicionar suporte a temas escuros`

2. Ao abrir um **pull request**, utilize o prefixo no título.
   - Exemplo: `fix: corrige erro de cálculo na função de desconto`

3. Ao fazer um **commit**, utilize o prefixo na mensagem.
   - Exemplo: `docs: atualiza README com instruções de instalação`

## Benefícios

- **Organização**: Facilita a categorização e filtragem de tarefas.
- **Clareza**: Torna o propósito de cada alteração mais evidente.
- **Consistência**: Mantém um padrão em todo o projeto.
