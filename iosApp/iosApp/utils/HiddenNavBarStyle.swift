//
//  HiddenNavBarStyle.swift
//  MoviesBudget
//
//  Created by admin on 2022. 01. 13..
//  Copyright © 2022. orgName. All rights reserved.
//
import SwiftUI

struct HiddenNavigationBar: ViewModifier {
    func body(content: Content) -> some View {
        content
        .navigationBarTitle("", displayMode: .inline)
        .navigationBarHidden(true)
    }
}

extension View {
    func hiddenNavigationBarStyle() -> some View {
        modifier( HiddenNavigationBar() )
    }
}

