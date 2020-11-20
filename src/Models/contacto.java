package Models;

public class contacto {

    int mIdContacto;
    int mTelefonoContacto;
    String mDetalles;
    int mIProveedor;
    String mNombreContacto, mCorreoContacto;

    public int getmIProveedor() {
        return mIProveedor;
    }

    public void setmIProveedor(int mIProveedor) {
        this.mIProveedor = mIProveedor;
    }


    public int getmIdContacto() {
        return mIdContacto;
    }

    public void setmIdContacto(int mIdContacto) {
        this.mIdContacto = mIdContacto;
    }

    public int getmTelefonoContacto() {
        return mTelefonoContacto;
    }

    public void setmTelefonoContacto(int mTelefonoContacto) {
        this.mTelefonoContacto = mTelefonoContacto;
    }

    public String getmNombreContacto() {
        return mNombreContacto;
    }

    public void setmNombreContacto(String mNombreContacto) {
        this.mNombreContacto = mNombreContacto;
    }

    public String getmCorreoContacto() {
        return mCorreoContacto;
    }

    public void setmCorreoContacto(String mCorreoContacto) {
        this.mCorreoContacto = mCorreoContacto;
    }

    public String getmDetalles() {
        return mDetalles;
    }

    public void setmDetalles(String mDetalles) {
        this.mDetalles = mDetalles;
    }

    public contacto(int mIdContacto, int mIProveedor, String mNombreContacto, String mDetalles, int mTelefonoContacto, String mCorreoContacto) {
        this.mIdContacto = mIdContacto;
        this.mIProveedor = mIProveedor;
        this.mTelefonoContacto = mTelefonoContacto;
        this.mDetalles = mDetalles;
        this.mNombreContacto = mNombreContacto;
        this.mCorreoContacto = mCorreoContacto;
    }
}

