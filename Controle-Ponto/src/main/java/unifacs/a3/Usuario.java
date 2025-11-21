package unifacs.a3;

public class Usuario {
  private  String nome;
  private String email;
  private  int senha;
  private   int id;
   



    public String getNome() {
        return nome;
    }
    public int getSenha() {
        return senha;
    }
    public int getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setSenha(int senha) {
        this.senha = senha;
    }
    public void setId(int id) {
        this.id = id;
    }

    
    
}
