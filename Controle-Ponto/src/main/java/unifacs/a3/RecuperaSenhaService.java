package unifacs.a3;

public class RecuperaSenhaService {

    private final UsuarioRepository usuarioRepository;

    public RecuperaSenhaService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public String recuperarSenha(String email, int novaSenha) throws Exception {
        if (!usuarioRepository.existePorEmail(email)) {
            return "Usuário não encontrado!";
        }

        usuarioRepository.atualizarSenha(email, novaSenha);
        return "Senha alterada!";
    }

}
