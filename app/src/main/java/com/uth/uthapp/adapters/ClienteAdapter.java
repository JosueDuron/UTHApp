package com.uth.uthapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uth.uthapp.R;
import com.uth.uthapp.models.Cliente;

import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder> {

    private List<Cliente> listaClientes;

    public ClienteAdapter(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.listaClientes = clientes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_item, parent, false);
        return new ClienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteViewHolder holder, int position) {
        Cliente cliente = listaClientes.get(position);
        holder.tvName.setText(cliente.getNombres() + " " + cliente.getApellidos());
        holder.tvDetails.setText("Edad: " + cliente.getEdad() + " | Correo: " + cliente.getCorreo());
        holder.tvId.setText("#" + cliente.getId());
    }

    @Override
    public int getItemCount() {
        return listaClientes != null ? listaClientes.size() : 0;
    }

    public static class ClienteViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDetails, tvId;

        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvClientName);
            tvDetails = itemView.findViewById(R.id.tvClientDetails);
            tvId = itemView.findViewById(R.id.tvClientId);
        }
    }
}
