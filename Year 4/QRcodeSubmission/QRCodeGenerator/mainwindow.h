#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <string>
#include <map>
#include "errorCorrection.h"
#include "messagefun.h"
#include "matrixfun.h"

QT_BEGIN_NAMESPACE
namespace Ui { class MainWindow; }
QT_END_NAMESPACE

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

private slots:
    void on_submitButton_clicked();

private:
    Ui::MainWindow *ui;

private:
    std::string * dataEncoding();
    int chooseVersion(std::string input);
    std::string characterCount(std::string input, int versionNumber);
    std::string decimalToBinary(std::string input, int binarySize);
    std::string getColor();
};
#endif // MAINWINDOW_H
