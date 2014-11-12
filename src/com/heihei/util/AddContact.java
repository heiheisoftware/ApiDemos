//
//package com.heihei.util;
//
//import android.app.Activity;
//import android.content.ContentProviderOperation;
//import android.content.Context;
//import android.os.Bundle;
//import android.provider.ContactsContract;
//import android.util.Log;
//
//import java.io.BufferedInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class AddContact extends Activity {
//    private static final String TAG = "MainActivity";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        try {
//            BufferedInputStream bis = new BufferedInputStream(getAssets().open("Mobile.txt"));
//            byte[] bf = new byte[1024];
//
//            StringBuilder sb = new StringBuilder();
//            int count = 0;
//            while (-1 != (count = bis.read(bf))) {
//                sb.append(new String(bf, 0, count));
//            }
//
//            String[] pArr = sb.toString().split("\n");
//            
//            Log.d(TAG, "onCreate: " + pArr.length);
//            
//            for (int i = 0; i < 5; i++) {
//                addContact(this, "", "", pArr[i], "", "", "", "");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void addContact(Context context, String name,
//            String organisation, String phone, String fax, String email, String address, String website) {
//
//        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
//
//        //在名片表插入一个新名片
//        ops.add(ContentProviderOperation
//                .newInsert(ContactsContract.RawContacts.CONTENT_URI)
//                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
//                .withValue(ContactsContract.RawContacts._ID, 0)
//                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
//                .withValue(
//                        ContactsContract.RawContacts.AGGREGATION_MODE,
//                        ContactsContract.RawContacts.AGGREGATION_MODE_DISABLED).build());
//
//        // add name
//        //添加一条新名字记录；对应RAW_CONTACT_ID为0的名片
//        if (!name.equals("")) {
//            ops.add(ContentProviderOperation
//                    .newInsert(ContactsContract.Data.CONTENT_URI)
//                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
//                    .withValue(
//                            ContactsContract.Data.MIMETYPE,
//                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE).withValue(
//                            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name).build());
//        }
//
//        //添加昵称
//        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(
//                        ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE)
//                .withValue(ContactsContract.CommonDataKinds.Nickname.NAME, "").build());
//
//        // add company
//        if (!organisation.equals("")) {
//            ops.add(ContentProviderOperation
//                    .newInsert(ContactsContract.Data.CONTENT_URI)
//                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
//                    .withValue(ContactsContract.Data.MIMETYPE,
//                            ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
//                    .withValue(
//                            ContactsContract.CommonDataKinds.Organization.COMPANY, organisation)
//                    .withValue(
//                            ContactsContract.CommonDataKinds.Organization.TYPE,
//                            ContactsContract.CommonDataKinds.Organization.TYPE_WORK).build());
//        }
//
//        // add phone
//        if (!phone.equals("")) {
//            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
//                    .withValue(ContactsContract.Data.MIMETYPE,
//                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
//                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
//                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, 1).build());
//        }
//
//        // add Fax
//        if (!fax.equals("")) {
//            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference(
//                    ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(
//                    ContactsContract.Data.MIMETYPE,
//                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE).withValue(
//                    ContactsContract.CommonDataKinds.Phone.NUMBER, fax)
//                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
//                            ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK).build());
//        }
//
//        // add email
//        if (!email.equals("")) {
//            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//                    .withValueBackReference(
//                            ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(
//                            ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
//                    .withValue(ContactsContract.CommonDataKinds.Email.DATA, email)
//                    .withValue(ContactsContract.CommonDataKinds.Email.TYPE, 1).build());
//        }
//
//        // add address
//        if (!address.equals("")) {
//            ops.add(ContentProviderOperation
//                    .newInsert(ContactsContract.Data.CONTENT_URI)
//                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
//                    .withValue(
//                            ContactsContract.Data.MIMETYPE,
//                            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE).withValue(
//                            ContactsContract.CommonDataKinds.StructuredPostal.STREET, address)
//                    .withValue(ContactsContract.CommonDataKinds.StructuredPostal.TYPE,
//                            ContactsContract.CommonDataKinds.StructuredPostal.TYPE_WORK).build());
//        }
//
//        // add website
//        if (!website.equals("")) {
//            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
//                    .withValue(ContactsContract.Data.MIMETYPE,
//                            ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE).withValue(
//                            ContactsContract.CommonDataKinds.Website.URL, website)
//                    .withValue(
//                            ContactsContract.CommonDataKinds.Website.TYPE,
//                            ContactsContract.CommonDataKinds.Website.TYPE_WORK).build());
//        }
//
////        // add IM
////        String qq1 = "452824089";
////        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference(
////                ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(
////                ContactsContract.Data.MIMETYPE,
////                ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE).withValue(
////                ContactsContract.CommonDataKinds.Im.DATA1, qq1)
////                .withValue(
////                        ContactsContract.CommonDataKinds.Im.PROTOCOL,
////                        ContactsContract.CommonDataKinds.Im.PROTOCOL_QQ).build());
//
//        // add logo image
//        // Bitmap bm = logo;
//        // if (bm != null) {
//        // ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        // bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        // byte[] photo = baos.toByteArray();
//        // if (photo != null) {
//        //
//        // ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//        // .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
//        // .withValue(ContactsContract.Data.MIMETYPE,
//        // ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)
//        // .withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, photo)
//        // .build());
//        // }
//        // }
//
//        try {
//            context.getContentResolver().applyBatch(
//                    ContactsContract.AUTHORITY, ops);
//        } catch (Exception e) {
//        }
//
//    }
//
//}
