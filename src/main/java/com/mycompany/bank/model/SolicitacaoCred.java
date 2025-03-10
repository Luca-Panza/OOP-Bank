package com.mycompany.bank.model;
import com.google.gson.annotations.Expose;


public class SolicitacaoCred {
    @Expose
    private String reason;
    @Expose
    private final int userId;
    @Expose
    private final double value;
    @Expose
    private boolean analyzed = false;
    @Expose
    private boolean approved = false;
    @Expose
    private boolean accepted = false;
    private static int contadorId = 0;
    @Expose
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
   
   @Override()
   public String toString() {
       return this.reason;
   }
}
