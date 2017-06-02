/* Exemplo de Programa na linguagem L */

integer n=1 + 2;
string nome='1'+'2';
boolean naoTerminou= 1 != 300;

/* Programa Principal */
main
  write('Digite seu nome: ');
  readln(nome);
  naoTerminou=true;
  n=0;
  while (naoTerminou) begin
    writeln('Ola '' ',nome);
    n=n+1;
    naoTerminou=n<MAXITER;
  end
end
