package aomine.model;

public class Reniec {
  private String nombres;
  private String apellidoPaterno;
  private String apellidoMaterno;
  private String tipoDocumento;
  private String numeroDocumento;
  private String digitoVerificador;

  
  public String getApellidoPaterno() {
    return apellidoPaterno;
  }

  public void setApellidoPaterno(String apellidoPaterno) {
    this.apellidoPaterno = apellidoPaterno;
  }

  public String getApellidoMaterno() {
    return apellidoMaterno;
  }
  public void setApellidoMaterno(String apellidoMaterno) {
    this.apellidoMaterno = apellidoMaterno;
  }
  
  public String getDigitoVerificador() {
    return digitoVerificador;
  }
  public void setDigitoVerificador(String digitoVerificador) {
    this.digitoVerificador = digitoVerificador;
  }

  public String getNombres() {
    return nombres;
  }
  public void setNombres(String nombres) {
    this.nombres = nombres;
  }
  public String getNumeroDocumento() {
    return numeroDocumento;
  }
  public void setNumeroDocumento(String numeroDocumento) {
    this.numeroDocumento = numeroDocumento;
  }
  public String getTipoDocumento() {
    return tipoDocumento;
  }
  public void setTipoDocumento(String tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }
}
