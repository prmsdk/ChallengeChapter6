package com.example.challengechapter6.helper

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.challengechapter6.R
import com.example.challengechapter6.dao.AppDatabase
import com.example.challengechapter6.dao.player.PlayerEntity
import com.example.challengechapter6.player.ui.EditPlayerActivity
import com.example.challengechapter6.player.ui.ShowPlayerActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class ShowPlayerAdapter(val listPlayer: List<PlayerEntity>) : RecyclerView.Adapter<ShowPlayerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.content_show_player, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sessionManager: SessionManager = SessionManager(holder.itemView.context)
        val tvId: TextView = holder.itemView.findViewById(R.id.tv_content_id)
        val tvName: TextView = holder.itemView.findViewById(R.id.tv_content_name)
        val tvEmail: TextView = holder.itemView.findViewById(R.id.tv_content_email)
        val btnEdit: Button = holder.itemView.findViewById(R.id.btn_edit)
        val btnHapus: Button = holder.itemView.findViewById(R.id.btn_delete)

        if (sessionManager.getPlayerDetail().get(sessionManager.EMAIL).toString() == (listPlayer[position].email)){
            btnHapus.visibility = View.GONE
        }

        tvId.text = listPlayer[position].id.toString()
        tvName.text = listPlayer[position].name
        tvEmail.text = listPlayer[position].email

        btnEdit.setOnClickListener(View.OnClickListener {
            val intentToEdit = Intent(holder.itemView.context, EditPlayerActivity::class.java)
            intentToEdit.putExtra("player", listPlayer[position])
            holder.itemView.context.startActivity(intentToEdit)
        })

        btnHapus.setOnClickListener(View.OnClickListener {
            AlertDialog.Builder(holder.itemView.context).setPositiveButton("Ya"){
                p0, p1 -> val mDb = AppDatabase.getInstance(holder.itemView.context)

                GlobalScope.async {
                    val result = mDb?.playerDao()?.deletePlayer(listPlayer[position])

                    (holder.itemView.context as ShowPlayerActivity).runOnUiThread{
                        if (result != 0){
                            Toast.makeText(holder.itemView.context, "Data ${listPlayer[position].name} berhasil dihapus", Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(holder.itemView.context, "Data ${listPlayer[position].name} gagal dihapus", Toast.LENGTH_LONG).show()
                        }
                    }

                    (holder.itemView.context as ShowPlayerActivity).fetchData()
                }
            }.setNegativeButton("Tidak"){
                p0, p1 -> p0.dismiss()
            }.setMessage("Apakah anda yakin ingin menghapus data ${listPlayer[position].name} ?")
                .setTitle("Konfirmasi Hapus")
                .create().show()
        })
    }

    override fun getItemCount(): Int {
        return listPlayer.size
    }
}