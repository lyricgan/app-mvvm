package uk.ivanc.archimvp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import uk.ivanc.archimvp.R;
import uk.ivanc.archimvp.model.Repository;
import uk.ivanc.archimvp.model.User;
import uk.ivanc.archimvp.presenter.RepositoryPresenter;

public class RepositoryActivity extends AppCompatActivity implements RepositoryMvpView {
    private static final String EXTRA_REPOSITORY = "EXTRA_REPOSITORY";

    private TextView descriptionText;
    private TextView homepageText;
    private TextView languageText;
    private TextView forkText;
    private TextView ownerNameText;
    private TextView ownerEmailText;
    private TextView ownerLocationText;
    private ImageView ownerImage;
    private View ownerLayout;

    private RepositoryPresenter presenter;

    public static Intent newIntent(Context context, Repository repository) {
        Intent intent = new Intent(context, RepositoryActivity.class);
        intent.putExtra(EXTRA_REPOSITORY, repository);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RepositoryPresenter();
        presenter.attachView(this);

        setContentView(R.layout.activity_repository);
        Toolbar toolbar = findViewById(R.id.toolbar);
        descriptionText = findViewById(R.id.text_repo_description);
        homepageText = findViewById(R.id.text_homepage);
        languageText = findViewById(R.id.text_language);
        forkText = findViewById(R.id.text_fork);
        ownerNameText = findViewById(R.id.text_owner_name);
        ownerEmailText = findViewById(R.id.text_owner_email);
        ownerLocationText = findViewById(R.id.text_owner_location);
        ownerImage = findViewById(R.id.image_owner);
        ownerLayout = findViewById(R.id.layout_owner);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Repository repository = getIntent().getParcelableExtra(EXTRA_REPOSITORY);
        bindRepositoryData(repository);
        presenter.loadOwner(repository.owner.url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showOwner(final User owner) {
        ownerNameText.setText(owner.name);
        ownerEmailText.setText(owner.email);
        ownerEmailText.setVisibility(owner.hasEmail() ? View.VISIBLE : View.GONE);
        ownerLocationText.setText(owner.location);
        ownerLocationText.setVisibility(owner.hasLocation() ? View.VISIBLE : View.GONE);
        ownerLayout.setVisibility(View.VISIBLE);
    }

    private void bindRepositoryData(final Repository repository) {
        setTitle(repository.name);
        descriptionText.setText(repository.description);
        homepageText.setText(repository.homepage);
        homepageText.setVisibility(repository.hasHomepage() ? View.VISIBLE : View.GONE);
        languageText.setText(getString(R.string.text_language, repository.language));
        languageText.setVisibility(repository.hasLanguage() ? View.VISIBLE : View.GONE);
        forkText.setVisibility(repository.isFork() ? View.VISIBLE : View.GONE);
        //Preload image for user because we already have it before loading the full user
        Picasso.with(this)
                .load(repository.owner.avatarUrl)
                .placeholder(R.drawable.placeholder)
                .into(ownerImage);
    }
}
