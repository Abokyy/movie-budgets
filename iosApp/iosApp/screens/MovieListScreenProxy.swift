//
//  MovieListScreenProxy.swift
//  MoviesBudget
//
//  Created by admin on 2022. 01. 13..
//  Copyright © 2022. orgName. All rights reserved.
//

import features

typealias MovieListState = MovieListViewModel.State
typealias MovieListWish = MovieListViewModel.Wish
typealias MovieListNews = MovieListViewModel.News


class MovieListScreenProxy: MovieListView, ObservableObject {
    @Published var state : MovieListState?
    @Published var news: MovieListNews?
    
    override func render(state: MovieListViewModel.State) {
        self.state = state
    }
    
    override func presentNews(news: MovieListViewModel.News) {
        self.news = news
    }
}
