    package stu.edu.vn.thigk.adapter;

    import android.app.Activity;
    import android.content.Context;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.net.Uri;
    import android.provider.MediaStore;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.ImageView;
    import android.widget.TextView;

    import java.io.IOException;
    import java.io.InputStream;
    import java.util.List;

    import stu.edu.vn.thigk.R;
    import stu.edu.vn.thigk.dao.DBHelper;
    import stu.edu.vn.thigk.dao.imageHandle;
    import stu.edu.vn.thigk.model.HangHoa;


    public class AdapterHanghoa extends ArrayAdapter<HangHoa> {
        Activity context;
        int res;
        List<HangHoa> obj;

        DBHelper helper;

        public AdapterHanghoa(Activity context, int res, List<HangHoa> list, DBHelper helper)
        {
            super(context,res, list);
            this.context =context;
            this.res = res;
            this.obj = list;
            this.helper = helper;

        }


        @Override
        public View getView(int position,  View convertView,  ViewGroup parent) {
            LayoutInflater inflater =this.context.getLayoutInflater();
            View item = inflater.inflate(this.res, null);

            TextView tenhang =item.findViewById(R.id.tvTenhang);
            TextView phanloai =item.findViewById(R.id.tvPhanloai);
            TextView gia =item.findViewById(R.id.tvgia);
            TextView dungtich =item.findViewById(R.id.tvDungtich);
            ImageView imageView=item.findViewById(R.id.imagehanghoa);
            final HangHoa hh = obj.get(position);
            tenhang.setText(hh.getTenHang());
            phanloai.setText(hh.getTenloai());

            gia.setText(hh.getGia()+"");
            dungtich.setText(hh.getDungtich()+"");
            if (hh.getHinhanh() != null) {
                Uri imageUri = Uri.parse(hh.getHinhanh());

                try {
                    // Sử dụng ContentResolver để mở InputStream từ Uri
                    InputStream imageStream = context.getContentResolver().openInputStream(imageUri);

                    if (imageStream != null) {
                        // Sử dụng BitmapFactory để tạo Bitmap từ InputStream
                        Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                        imageStream.close(); // Đóng InputStream sau khi sử dụng xong

                        if (bitmap != null) {
                            imageView.setImageBitmap(bitmap);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return item;
        }

    }


