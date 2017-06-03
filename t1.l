/* Exemplo de Programa na linguagem L */

/*integer n=1 + 2;*/
string nome=-'string';
boolean naoTerminou= 1 != 300;
byte n=0;
/* Programa Principal */
main
  write('Digite seu nome: ');
  readln(nome);
  naoTerminou=true;
  while (naoTerminou) begin
    writeln('Ola '' ',nome);
    n=n*1;
    naoTerminou=n<MAXITER;
  end
end
