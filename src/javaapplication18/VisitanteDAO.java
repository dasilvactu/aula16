/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication18;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ice
 */
public class VisitanteDAO {
    private Connection conexao;
    private PreparedStatement operacaoInsere;
    private PreparedStatement operacaoListar;
    private PreparedStatement operacaoSaida;
    
    public VisitanteDAO() throws Exception {
        conexao = ConexaoJavaDB.getConnection();
        operacaoInsere = conexao.prepareStatement("INSERT INTO visitante (nome,idade) VALUES (?,?)");
        operacaoListar = conexao.prepareStatement("SELECT * FROM visitante");
        operacaoSaida = conexao.prepareStatement("UPDATE visitante SET saida = CURRENT_TIMESTAMP WHERE id=? AND saida IS NULL");
    }
    public void criar(Visitante v) throws SQLException{
        operacaoInsere.clearParameters();
        operacaoInsere.setString(1, v.getNome());
        operacaoInsere.setInt(2, v.getIdade());
        operacaoInsere.executeUpdate();
    }
    public void saida(Long id) throws SQLException{
        operacaoSaida.clearParameters();
        operacaoSaida.setLong(1, id);
        operacaoSaida.executeUpdate();
    }
    public List<Visitante> listar() throws SQLException{
        List<Visitante> visitantes = new ArrayList<>();
        operacaoListar.clearParameters();
        ResultSet resultado = operacaoListar.executeQuery();
        while (resultado.next()) {
            Visitante v = new Visitante();
            v.setId(resultado.getLong(1));
            v.setNome(resultado.getString(2));
            v.setIdade(resultado.getInt(3));
            v.setEntrada(resultado.getTimestamp(4));
            v.setSaida(resultado.getTimestamp(5));
            visitantes.add(v);
        }
        return visitantes;
        
    }
}
