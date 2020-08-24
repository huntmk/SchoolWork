#ifndef QRPAGE_H
#define QRPAGE_H

#include <QMainWindow>

namespace Ui {
class QRPage;
}

class QRPage : public QMainWindow
{
    Q_OBJECT

public:
    explicit QRPage(QWidget *parent = nullptr);
    ~QRPage();

private:
    Ui::QRPage *ui;
};

#endif // QRPAGE_H
