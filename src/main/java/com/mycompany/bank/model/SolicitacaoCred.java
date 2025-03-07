package com.mycompany.bank.model;


public class SolicitacaoCred {
    private String reason;
    private final int userId;
    private final double value;
    private boolean analyzed = false;
    private boolean approved = false;
    private boolean accepted = false;
    private static int contadorId = 0;
    private int id;

    public SolicitacaoCred(int userId, double value, String reason) {
        this.userId = userId;
        this.value = value;
        this.reason = reason;
        this.id = contadorId++;
    }
    
   public int getUserId() {
       return this.userId;
   }
   
   public int getId() {
       return this.id;
   }
   
   public double getValue() {
       return this.value;
   }
   
   public boolean isApproved() {
       return this.approved;
   }
   
   public boolean isAccepted() {
       return this.accepted;
   }
   
   public boolean isAnalyzed() {
       return this.analyzed;
   }
   
   public void setanAlyzed() {
       this.analyzed = true;
   }
   
   public void setApproved() {
       this.approved = true;
   }
   
   public void setAccepted() {
       this.accepted = true;
   }
   
   public String getReason() {
       return this.reason;
   }
}
