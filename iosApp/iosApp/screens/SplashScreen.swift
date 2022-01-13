//
//  SplashScreen.swift
//  MoviesBudget
//
//  Created by admin on 2022. 01. 13..
//  Copyright © 2022. orgName. All rights reserved.
//

import SwiftUI

struct SplashScreen: View {
    @State var isAnimating = false
    @State var size: CGFloat = 1
    @State var navigate: Bool = false
    var repeatingAnimation: Animation {
        Animation
            .easeInOut(duration: 1)
            .repeatForever()
    }
    var body: some View {
        
        ZStack {
            
            NavigationLink(destination: MovieListScreen(proxy: MovieListScreenProxy()).hiddenNavigationBarStyle(), isActive: self.$navigate) { EmptyView()}
            
            if isAnimating {
                VStack {
                    Image("logo")
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 150, height: 150)
                        .scaleEffect(size)
                        .onAppear {
                            withAnimation(self.repeatingAnimation) { self.size = 1.3 }
                        }
                }
            }
        }
        .onAppear {
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.01) {
                self.isAnimating = true
            }
            DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
                self.navigate = true
            }
        }
    }
}

