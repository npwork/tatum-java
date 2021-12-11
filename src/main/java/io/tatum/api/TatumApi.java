package io.tatum.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.tatum.utils.ApiKey;
import java.lang.annotation.Annotation;
import io.tatum.utils.BaseUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.Nullable;
import retrofit2.Converter;
import java.lang.reflect.Type;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class TatumApi {
    public static final String API_KEY_HEADER = "x-api-key";

    public final BitcoinApi bitcoin;
    public final EthereumApi ethereum;
    public final BcashApi bcash;
    public final LitecoinApi litecoin;
    public final TronApi tron;
    public final XrpApi xrp;
    public final XlmApi xlm;
    public final VetApi vet;

    public TatumApi() {
        this(ApiKey.getInstance().getApiKey());
    }

    public TatumApi(String apiKey) {
        this(apiKey, BaseUrl.getInstance().getUrl());
    }

    public TatumApi(String apiKey, String baseUrl) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(loggingInterceptor).addInterceptor(chain -> {
            Request originalRequest = chain.request();

            Request.Builder builder = originalRequest.newBuilder().header(API_KEY_HEADER, apiKey);

            Request newRequest = builder.build();
            return chain.proceed(newRequest);
        }).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(okHttpClient).build();

        bitcoin = retrofit.create(BitcoinApi.class);
        ethereum = retrofit.create(EthereumApi.class);
        bcash = retrofit.create(BcashApi.class);
        litecoin = retrofit.create(LitecoinApi.class);
        tron = retrofit.create(TronApi.class);
        xrp = retrofit.create(XrpApi.class);
        xlm = retrofit.create(XlmApi.class);
        vet = retrofit.create(VetApi.class);
    }
}

class NullOnEmptyConverterFactory extends Converter.Factory {
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
        return (Converter<ResponseBody, Object>) body -> {
            if (body.source().exhausted()) return null;
            return delegate.convert(body);
        };
    }
}
