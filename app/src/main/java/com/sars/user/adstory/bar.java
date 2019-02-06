package com.sars.user.adstory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class bar extends AppCompatActivity {
        private DrawerLayout d;
        private ActionBarDrawerToggle a;
        private NavigationView n;
    private FirebaseAuth mAuth;
    FirebaseDatabase database,database1;
    DatabaseReference myRef,myref1;
    private RecyclerView r;
    private DatabaseReference f;
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    TextView t1,t2;
    FirebaseRecyclerAdapter<blog,Bloghold> firebaseRecyclerAdapter;


    private Toolbar toolbar;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_bar);

             database = FirebaseDatabase.getInstance();
            database1 = FirebaseDatabase.getInstance();
        f = FirebaseDatabase.getInstance().getReference().child("global");
        //f.keepSynced(true);
        r = (RecyclerView) findViewById(R.id.rec);
        r.setHasFixedSize(true);
        r.setLayoutManager(new LinearLayoutManager(this));
        //Toast.makeText(MainActivity.this,"new",Toast.LENGTH_SHORT).show();


        Toast.makeText(bar.this,"Wait for internet connection",Toast.LENGTH_SHORT).show();
        ImageView imageView5=(ImageView)findViewById(R.id.imageView5);
        ImageView imageView56=(ImageView)findViewById(R.id.imageView56);
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iscreen=new Intent(bar.this,screen.class);
                startActivity(iscreen);
            }
        });
        imageView56.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iscreenshot=new Intent(bar.this,screenshot.class);
                startActivity(iscreenshot);
            }
        });



        d = (DrawerLayout) findViewById(R.id.draw);
            a = new ActionBarDrawerToggle(this, d, R.string.open, R.string.close);
            d.addDrawerListener(a);
            a.syncState();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            n = (NavigationView) findViewById(R.id.nav);
            n.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    int id = menuItem.getItemId();

                    if (id == R.id.con) {
                        Intent ico = new Intent(bar.this, contact.class);
                        startActivity(ico);
                    }
                    if (id == R.id.fee) {
                        Intent ifee = new Intent(bar.this, feedback.class);
                        startActivity(ifee);
                    }
                    if(id==R.id.ana)
                    {
                        Intent iana=new Intent(bar.this,act.class);
                        startActivity(iana);
                    }
                    if(id==R.id.acc)
                    {
                        Intent iacc=new Intent(bar.this,account.class);
                        startActivity(iacc);
                    }

                    return false;
                }
            });


        String id= FirebaseAuth.getInstance().getCurrentUser().getUid();
        myRef = database.getReference(id).child("name");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name= (String) dataSnapshot.getValue().toString();
                TextView textview=(TextView)n.getHeaderView(0).findViewById(R.id.name);
                textview.setText(name);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myref1=database1.getReference(id).child("mobile");
        myref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String num= (String) dataSnapshot.getValue().toString();
                TextView textview1=(TextView)n.getHeaderView(0).findViewById(R.id.num);
                textview1.setText(num);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        }
    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<blog> options =
                new FirebaseRecyclerOptions.Builder<blog>()
                        .setQuery(f, blog.class)
                        .build();
        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<blog, Bloghold>
                (options) {

            @Override
            public Bloghold onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.car,viewGroup, false);

                return new Bloghold(view);

            }

            @Override
            protected void onBindViewHolder(Bloghold holder, int position, blog model) {
                final String k=getRef(position).getKey();

                holder.setTitle(model.getTitle());
                holder.setRate(model.getRate());
                holder.setImage(getApplicationContext(),model.getImage());
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent ipshare=new Intent(bar.this,pshare.class);
                        ipshare.putExtra("code",k);
                        startActivity(ipshare);
                    }
                });

            }


        };
        r.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

    }


    public static class Bloghold extends RecyclerView.ViewHolder
    {   View v;

        public Bloghold(@NonNull View itemView) {
            super(itemView);
            v=itemView;

        }
        public void setTitle(String title )
        {
            TextView t1=(TextView)v.findViewById(R.id.textView);
            t1.setText(title);
        }
        public void setRate(String rate )
        {
            TextView  t2=(TextView)v.findViewById(R.id.textView2);
            t2.setText(rate);
        }
        public void setImage(Context c, String image)
        {
            ImageView i=(ImageView)v.findViewById(R.id.imageView);
            Picasso.with(c).load(image).into(i);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }





    @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (a.onOptionsItemSelected(item)) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onBackPressed() {
            if (d.isDrawerOpen(GravityCompat.START)) {
                d.closeDrawer(GravityCompat.START);
            } else {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
                super.onBackPressed();
            }

        }

    }

