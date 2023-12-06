package br.com.bda.emprestimosgelo.emprestimos;

import java.math.BigDecimal;
import java.sql.Date;

public class Emprestimos {

	    private int id;
	    private int usuarioId;
	    private BigDecimal valorInicialEmprestimo;
	    private BigDecimal valorPago;
	    private BigDecimal valorFaltante;
	    private Date dataVencimento;
	    private Date dataEmprestimo;

	    public Emprestimos(int id, int usuarioId, BigDecimal valorInicialEmprestimo, BigDecimal valorPago, BigDecimal valorFaltante, Date dataVencimento, Date dataEmprestimo) {
	        this.id = id;
	        this.usuarioId = usuarioId;
	        this.valorInicialEmprestimo = valorInicialEmprestimo;
	        this.valorPago = valorPago;
	        this.valorFaltante = valorFaltante;
	        this.dataVencimento = dataVencimento;
	        this.dataEmprestimo = dataEmprestimo;
	    }
	    
	    

		public Emprestimos(int usuarioId, BigDecimal valorInicialEmprestimo, BigDecimal valorPago,
				BigDecimal valorFaltante, Date dataVencimento, Date dataEmprestimo) {
			super();
			this.usuarioId = usuarioId;
			this.valorInicialEmprestimo = valorInicialEmprestimo;
			this.valorPago = valorPago;
			this.valorFaltante = valorFaltante;
			this.dataVencimento = dataVencimento;
			this.dataEmprestimo = dataEmprestimo;
		}






		public int getId() {
			return id;
		}




		public void setId(int id) {
			this.id = id;
		}




		public int getUsuarioId() {
			return usuarioId;
		}




		public void setUsuarioId(int usuarioId) {
			this.usuarioId = usuarioId;
		}




		public BigDecimal getValorInicialEmprestimo() {
			return valorInicialEmprestimo;
		}




		public void setValorInicialEmprestimo(BigDecimal valorInicialEmprestimo) {
			this.valorInicialEmprestimo = valorInicialEmprestimo;
		}




		public BigDecimal getValorPago() {
			return valorPago;
		}




		public void setValorPago(BigDecimal valorPago) {
			this.valorPago = valorPago;
		}




		public BigDecimal getValorFaltante() {
			return valorFaltante;
		}




		public void setValorFaltante(BigDecimal valorFaltante) {
			this.valorFaltante = valorFaltante;
		}




		public Date getDataVencimento() {
			return dataVencimento;
		}




		public void setDataVencimento(Date dataVencimento) {
			this.dataVencimento = dataVencimento;
		}




		public Date getDataEmprestimo() {
			return dataEmprestimo;
		}




		public void setDataEmprestimo(Date dataEmprestimo) {
			this.dataEmprestimo = dataEmprestimo;
		}




		@Override
	    public String toString() {
	        return "Emprestimo{" +
	                "id=" + id +
	                ", usuarioId=" + usuarioId +
	                ", valorInicialEmprestimo=" + valorInicialEmprestimo +
	                ", valorPago=" + valorPago +
	                ", valorFaltante=" + valorFaltante +
	                ", dataVencimento=" + dataVencimento +
	                ", dataEmprestimo=" + dataEmprestimo +
	                '}';
	    }
	}
