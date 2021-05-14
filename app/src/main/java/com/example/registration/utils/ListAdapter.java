package com.example.registration.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.registration.R;
import com.example.registration.models.Item;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListAdapter extends ArrayAdapter<Item> {

    private int resourceLayout;
    private Context mContext;

    public ListAdapter(Context context, int resource, ArrayList<Item> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        final Item p = getItem(position);

        if (p != null) {
            LinearLayout itempanel = (LinearLayout) v.findViewById(R.id.itempanel);
            TextView regonumber = (TextView) v.findViewById(R.id.regonumber);
            TextView type = (TextView) v.findViewById(R.id.type);
            TextView make = (TextView) v.findViewById(R.id.make);
            TextView model = (TextView) v.findViewById(R.id.model);
            TextView colour = (TextView) v.findViewById(R.id.colour);
            TextView vin = (TextView) v.findViewById(R.id.vin);
            TextView tare_weight = (TextView) v.findViewById(R.id.tare_weight);
            TextView gross_mass = (TextView) v.findViewById(R.id.gross_mass);
            TextView expiry_status = (TextView) v.findViewById(R.id.expiry_status);


            if (regonumber != null) {
                regonumber.setText(String.valueOf(p.getInsurer().getCode()));
            }
            if (type != null) {
                type.setText(p.getVehicle().getType());
            }
            if (make != null) {
                make.setText(p.getVehicle().getMake());
            }
            if (model != null) {
                model.setText(p.getVehicle().getModel());
            }
            if (colour != null) {
                colour.setText(p.getVehicle().getColour());
            }
            if (vin != null) {
                String vin_ = p.getVehicle().getVin();
                vin.setText(vin_.substring(vin_.length() - 4));
            }
            if (tare_weight != null) {
                tare_weight.setText(String.valueOf(p.getVehicle().getTareWeight()));
            }
            if (gross_mass != null) {
                gross_mass.setText(String.valueOf(p.getVehicle().getGrossMass()));
            }
            if (expiry_status != null) {
                expiry_status.setText(getExpiredStatus(p.getRegistration().getExpiryDate()) ? String.valueOf(monthDiff(p.getRegistration().getExpiryDate())) : "Expired" );
            }

            itempanel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog itemDialog = new AlertDialog.Builder(mContext).create();

                    LayoutInflater item;
                    item = LayoutInflater.from(mContext);
                    View inflated = item.inflate(R.layout.item_modal, null);

                    TextView plate_number_modal = (TextView) inflated.findViewById(R.id.plate_number_modal);
                    TextView expired_modal = (TextView) inflated.findViewById(R.id.expired_modal);
                    TextView type_modal = (TextView) inflated.findViewById(R.id.type_modal);
                    TextView make_modal = (TextView) inflated.findViewById(R.id.make_modal);
                    TextView model_modal = (TextView) inflated.findViewById(R.id.model_modal);
                    TextView colour_modal = (TextView) inflated.findViewById(R.id.colour_modal);
                    TextView vin_modal = (TextView) inflated.findViewById(R.id.vin_modal);
                    TextView tare_weight_modal = (TextView) inflated.findViewById(R.id.tare_weight_modal);
                    TextView gross_mass_modal = (TextView) inflated.findViewById(R.id.gross_mass_modal);
                    TextView insurer_code_modal = (TextView) inflated.findViewById(R.id.insurer_code_modal);
                    TextView insurer_name_modal = (TextView) inflated.findViewById(R.id.insurer_name_modal);

                    plate_number_modal.setText(p.getPlateNumber());
                    expired_modal.setText(getExpiredStatus(p.getRegistration().getExpiryDate()) ? String.valueOf(monthDiff(p.getRegistration().getExpiryDate())) : "Expired" );
                    type_modal.setText(p.getVehicle().getType());
                    make_modal.setText(p.getVehicle().getMake());
                    model_modal.setText(p.getVehicle().getModel());
                    colour_modal.setText(p.getVehicle().getColour());
                    vin_modal.setText(p.getVehicle().getVin());
                    tare_weight_modal.setText(String.valueOf(p.getVehicle().getTareWeight()));
                    gross_mass_modal.setText(String.valueOf(p.getVehicle().getGrossMass()));
                    insurer_code_modal.setText(String.valueOf(p.getInsurer().getCode()));
                    insurer_name_modal.setText(p.getInsurer().getName());

                    itemDialog.setView(inflated);
                    itemDialog.show();
                    itemDialog.setCanceledOnTouchOutside(true);

                }
            });

        }

        return v;
    }

    private Boolean getExpiredStatus(String date) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date certain = df.parse(date.substring(0, 9));
            Date now = new Date();

            return certain.after(now);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private String monthDiff(String date) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date certain = df.parse(date.substring(0, 9));
            Date now = new Date();

            int months = 0;

            months += (certain.getYear() - now.getYear())*12;
            months -= now.getMonth();
            months += certain.getMonth();

            int months_adjusted = months <= 0 ? 0 : months;

            return months_adjusted + " months";

        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }

    }

}
