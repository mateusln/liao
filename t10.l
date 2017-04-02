/** Exemplo de Programa na linguagem L - tem erro [teste da constante hexadecimal - 0hDD, onde DD e [A-F] so aceita maiusculo]:  exibir erro lexema nao identificado */

integer n = 0h3a;
string nome;
boolean naoTerminou;
const MAXITER=10;

/* Programa Principal */
main
  write('Digite seu nome: ');
  readln(nome);
  naoTerminou=true;
  n=0;
  while (naoTerminou) begin
    writeln('Ola'' ',nome);
    n=n+1;
    naoTerminou=n<MAXITER;
  end
end

