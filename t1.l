/* Exemplo de Programa na linguagem L */

boolean n=true and false;
boolean n=(true);
string nome='1';
boolean naoTerminou;

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
