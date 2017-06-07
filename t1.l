/* Exemplo de Programa na linguagem L - ERRO*/

integer n;
string nome;
byte x;
boolean naoTerminou;
const MAXITER=10;

/* Programa Principal */
main
  write('Digite seu nome: ');
  readln(nome);
  naoTerminou=true;
  n=0;
  x = 0;
 nome = 'oi';
  while (x != naoTerminou) begin
    writeln('Ola'' ',nome);
    n=n+1;
    naoTerminou=n<MAXITER;
  end
end
