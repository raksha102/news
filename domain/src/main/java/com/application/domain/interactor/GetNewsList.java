package com.application.domain.interactor;

import com.application.domain.Constants;
import com.application.domain.News;
import com.application.domain.executor.PostExecutionThread;
import com.application.domain.executor.ThreadExecutor;
import com.application.domain.repositoty.NewsRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class GetNewsList extends UseCase<Observable<List<News>>, GetNewsList.Params> {

    private final NewsRepository mNewsRepository;

    @Inject
    public GetNewsList(NewsRepository newsRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mNewsRepository = newsRepository;
    }

    @Override
    Observable<List<News>> buildUseCaseObservable(Params params) {
        return mNewsRepository.getNews(params.source, params.page, Constants.PAGE_SIZE);
    }

    @Override
    public Observable<List<News>> execute(Params params) {
        return this.buildUseCaseObservable(params)
                .compose(getApiExecutor());
    }

    public static class Params {
        private final int page;
        private String source;

        /**
         *
         * @param source : news source
         * @param page : next page to fetched
         */
        public Params(String source, int page) {
            this.source = source;
            this.page = page;
        }
    }
}
